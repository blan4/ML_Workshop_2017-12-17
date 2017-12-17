# -*- coding: utf-8 -*-


def dense_neural_net():
    # Import model_dnn
    # build it
    # train it
    # copy opt_dnn.pb to android assets
    pass


def conv_neural_net():
    # Import model_conv
    # build it
    # train it
    # copy opt_conv.pb to android assets
    pass


def main():
    from argparse import ArgumentParser
    parser = ArgumentParser("MNIST android demo")
    parser.add_argument("-dnn", '--dense_neural_net', action='store_true', help='train dense neural net')
    parser.add_argument("-conv", '--conv_neural_net', action='store_true', help='train conv deep neural net')

    args = parser.parse_args()

    if args.dense_neural_net:
        dense_neural_net()
    if args.conv_neural_net:
        conv_neural_net()


if __name__ == '__main__':
    main()
