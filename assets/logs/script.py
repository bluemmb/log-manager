
import argparse
import datetime
import random
import os

parser = argparse.ArgumentParser("log generator")
parser.add_argument("-m", "--minutes_from", help="", type=int, default=1)
parser.add_argument("-t", "--minutes_to", help="", type=int, default=0)
parser.add_argument("-c", "--count", help="", type=int, default=10)
parser.add_argument("-C", "--component", help="", type=str, default="*")
parser.add_argument("-T", "--type", help="", type=str, default="*")
args = parser.parse_args()

def main():
    now = datetime.datetime.now().replace(second=0, microsecond=0)
    start_time = now - datetime.timedelta(minutes=args.minutes_from)
    end_time = now - datetime.timedelta(minutes=args.minutes_to)
    interval = ((args.minutes_from - args.minutes_to) * 60 * 1000) / args.count

    file = filename(end_time)
    source = os.path.dirname(os.path.abspath(__file__)) + "/temp/" + file
    destination = os.path.dirname(os.path.abspath(__file__)) + "/files/" + file

    with open(source, 'w') as f:
        for i in range(args.count):
            time = start_time + datetime.timedelta(milliseconds=i * interval)
            log = '{0} [{1}] {2} {3} - {4}'.format( formatted_time(time), thread(), type(), class_name(), message() )
            f.write("%s\n" % log)

    os.rename(source, destination)


def filename(time):
    return '{0}-{1}.log'.format(component(), time.strftime('%Y_%m_%d-%H_%M_%S'))


def formatted_time(time):
    return time.strftime('%Y-%m-%d %H:%M:%S,%f')[:-3]


def thread():
    return random.choice(["MainThread", "Thread1", "Thread2"])


def type():
    if args.type != "*":
        return args.type
    return random.choice(["DEBUG", "INFO", "WARNING", "ERROR", "CRITICAL"])


def component():
    if args.component != "*":
        return args.component
    return random.choice(["tomcat", "kafka", "mysql", "nginx", "mariadb", "postgres"])


def class_name():
    return "package.name." + random.choice(["Class", "MainClass", "HelperClass", "MainClass.SubClass"])


def message():
    return random.choice([
        "Everything is down!",
        "Something went wrong",
        "Method X() does not work",
        "This class has so many bugs",
        "I don't like this line of your code!",
    ])

main()
