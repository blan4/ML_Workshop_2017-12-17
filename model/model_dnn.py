# -*- coding: utf-8 -*-
import os
from datetime import datetime

import tensorflow as tf
from keras import metrics, Sequential
from keras.callbacks import TensorBoard, ModelCheckpoint
from keras.engine import Model
from keras.layers import Dense, Dropout, K
from keras.optimizers import Adam

from exporter import export_model

num_classes = 10

"""
Here we have a simple dummy neural net, that might overfit and be not accurate
"""


def build_model() -> Model:
    model = Sequential(layers=[
        Dense(200, activation='relu', input_shape=(784,)),
        # Dropout(0.5),
        # Dense(100, activation='relu'),
        # Dropout(0.5),
        # Dense(60, activation='relu'),
        # Dropout(0.5),
        # Dense(60, activation='relu'),
        Dense(num_classes, activation='softmax')
    ])

    model.summary()
    return model


def train(model: Model, x_train, y_train, x_test, y_test, batch_size=128, epochs=20):
    time = int(datetime.now().timestamp())
    name = "{}_{}".format("dnn", time)
    chkp_path = "./checkpoints/{}".format(name)
    os.makedirs(chkp_path, exist_ok=True)

    model.compile(loss=K.categorical_crossentropy,
                  optimizer=Adam(),
                  metrics=[metrics.categorical_accuracy])

    model.fit(x_train, y_train,
              batch_size=batch_size,
              epochs=epochs,
              validation_data=(x_test, y_test),
              callbacks=[TensorBoard(
                  log_dir='/tmp/tensorflow/{}'.format(name)
              ), ModelCheckpoint(
                  os.path.join(chkp_path,
                               "weights-improvement-{epoch:02d}-{val_categorical_accuracy:.2f}.hdf5"),
                  monitor='val_categorical_accuracy',
                  verbose=1,
                  save_best_only=True,
                  mode='auto'
              )])

    export_model(tf.train.Saver(), ['dense_1_input'], 'dense_2/Softmax', name)

    score = model.evaluate(x_test, y_test)

    print('Test loss:', score[0])
    print('Test accuracy:', score[1])
