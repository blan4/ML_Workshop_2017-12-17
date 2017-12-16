# -*- coding: utf-8 -*-

import tensorflow as tf
from tensorflow.python.platform import gfile
import sys


def main(model_filename, logdir='/tmp/tensorflow/android'):
    with tf.Session() as sess:
        with gfile.FastGFile(model_filename, 'rb') as f:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(f.read())
            g_in = tf.import_graph_def(graph_def)
    train_writer = tf.summary.FileWriter(logdir)
    train_writer.add_graph(sess.graph)


if __name__ == '__main__':
    main(sys.argv[1])
