# -*- coding: utf-8 -*-

import numpy as np
from keras.datasets import mnist
from keras.utils import to_categorical


def load_data(flatten=True):
    """
    Load MNIST dataset, do train-test split, convert data to the necessary format
    :return:
    """
    if flatten:
        reshape = _flatten
    else:
        reshape = _square

    (x_train, y_train), (x_test, y_test) = mnist.load_data()
    x_train = reshape(x_train)
    x_test = reshape(x_test)
    x_train = x_train.astype('float32')
    x_test = x_test.astype('float32')
    x_train /= 255
    x_test /= 255
    y_train = to_categorical(y_train, 10)
    y_test = to_categorical(y_test, 10)
    return x_train, y_train, x_test, y_test


def _flatten(x: np.ndarray) -> np.ndarray:
    return x.reshape(x.shape[0], 28 * 28)


def _square(x: np.ndarray) -> np.ndarray:
    return x.reshape(x.shape[0], 28, 28, 1)
