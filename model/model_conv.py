# -*- coding: utf-8 -*-

import os
from datetime import datetime

import tensorflow as tf
from keras import metrics, Sequential
from keras.callbacks import TensorBoard, ModelCheckpoint
from keras.engine import Model
from keras.layers import Conv2D, MaxPooling2D
from keras.layers import Dense, K
from keras.layers import Flatten
from keras.optimizers import Adam

from exporter import export_model

"""
Here we have a model with deep convulational neural network
"""

def build_model():
    model = Sequential()
    model.add(Conv2D(filters=16, kernel_size=5, strides=1,
                     padding='same', activation='relu',
                     input_shape=[28, 28, 1]))
    # 28*28*64
    model.add(MaxPooling2D(pool_size=2, strides=2, padding='same'))
    # 14*14*64

    model.add(Conv2D(filters=32, kernel_size=4, strides=1,
                     padding='same', activation='relu'))
    # 14*14*128
    model.add(MaxPooling2D(pool_size=2, strides=2, padding='same'))
    # 7*7*128

    model.add(Conv2D(filters=64, kernel_size=3, strides=1,
                     padding='same', activation='relu'))
    # 7*7*256
    model.add(MaxPooling2D(pool_size=2, strides=2, padding='same'))
    # 4*4*256

    model.add(Flatten())
    model.add(Dense(64 * 4, activation='relu'))
    model.add(Dense(10, activation='softmax'))

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

    export_model(tf.train.Saver(), ['conv2d_1_input'], 'dense_2/Softmax', name)

    score = model.evaluate(x_test, y_test)

    print('Test loss:', score[0])
    print('Test accuracy:', score[1])
