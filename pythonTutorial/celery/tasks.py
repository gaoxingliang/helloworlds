from celery import Celery

app = Celery('tasks', backend="redis://localhost:7379", broker="redis://localhost:7379")

@app.task
def add(x, y):
    print("Add func x=", x, " y=", y)
    return x + y

@app.task
def subValue2(x):
    print("Sub func x=", x)
    return x-2

@app.task
def sumAll(l):
    r = 0
    for x in l:
        r += x
    return r