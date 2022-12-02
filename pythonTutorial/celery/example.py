from tasks import add, subValue2, sumAll
from celery import group, chord

# callbacks
#print(add.apply_async((2, 2), link=subValue2.s()).get())


# print((add.s(4, 9) | subValue2.s() | add.s(8))().get())


res = group(add.s(i, i) for i in range(20))()
print(res.get(timeout=1))

res = chord((add.s(i, i) for i in range(10)), sumAll.s())()
print(res.get())