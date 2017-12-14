# -*- coding: utf-8 -*-
import os
from datetime import datetime

from keras import Input, metrics
from keras.callbacks import TensorBoard, ModelCheckpoint
from keras.engine import Model
from keras.layers import Dense, Dropout, K
from keras.optimizers import Adam

from exporter import export_model

num_classes = 10


def build_model() -> Model:
    inp = Input(shape=(784,), name='input')
    x = Dense(200, activation='relu')(inp)
    x = Dropout(0.5)(x)
    x = Dense(100, activation='relu')(x)
    x = Dropout(0.5)(x)
    x = Dense(60, activation='relu')(x)
    x = Dropout(0.5)(x)
    x = Dense(30, activation='relu')(x)
    x = Dropout(0.5)(x)
    x = Dense(num_classes, activation='softmax', name='output')(x)

    model = Model(inp, x, name='dnn')
    model.summary()
    return model


def train(model: Model, x_train, y_train, x_test, y_test, batch_size=128, epochs=20):
    model.compile(loss=K.categorical_crossentropy, optimizer=Adam(), metrics=[metrics.categorical_accuracy])
    time = int(datetime.now().timestamp())
    name = "{}_{}".format("dnn", time)
    chkp_path = "./checkpoints/{}".format(name)
    os.makedirs(chkp_path, exist_ok=True)

    history = model.fit(x_train, y_train,
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
    score = model.evaluate(x_test, y_test)

    print('Test loss:', score[0])
    print('Test accuracy:', score[1])

    export_model(['input'], ['output/Softmax'], name)
