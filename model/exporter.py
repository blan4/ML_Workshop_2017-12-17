# -*- coding: utf-8 -*-
import os

import tensorflow as tf
from keras import backend as K
from tensorflow.python.tools import freeze_graph
from tensorflow.python.tools import optimize_for_inference_lib


def export_model(saver: tf.train.Saver, input_names: list, output_name: str, model_name: str):
    """
    You can find node names by using debugger: just connect it right after model is created and look for nodes in the inspec
    :param saver:
    :param input_names:
    :param output_name:
    :param model_name:
    :return:
    """
    os.makedirs("./out", exist_ok=True)
    tf.train.write_graph(K.get_session().graph_def, 'out',
                         model_name + '_graph.pbtxt')

    saver.save(K.get_session(), 'out/' + model_name + '.chkp')

    # pbtxt is human readable representation of the graph
    freeze_graph.freeze_graph('out/' + model_name + '_graph.pbtxt', None,
                              False, 'out/' + model_name + '.chkp', output_name,
                              "save/restore_all", "save/Const:0",
                              'out/frozen_' + model_name + '.pb', True, "")

    input_graph_def = tf.GraphDef()
    with tf.gfile.Open('out/frozen_' + model_name + '.pb', "rb") as f:
        input_graph_def.ParseFromString(f.read())

    # optimization of the graph so we can use it in the android app
    output_graph_def = optimize_for_inference_lib.optimize_for_inference(
        input_graph_def, input_names, [output_name],
        tf.float32.as_datatype_enum)

    # This is archived optimal graph in the protobuf format we'll use in our android App.
    with tf.gfile.FastGFile('out/opt_' + model_name + '.pb', "wb") as f:
        f.write(output_graph_def.SerializeToString())

    print("graph saved!")
