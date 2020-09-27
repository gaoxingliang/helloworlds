from django.shortcuts import render
from django.http import HttpResponse
# Create your views here.
from myapp.forms import LoginForm
from myapp.models import Dreamreal
from myproject.settings import BASE_DIR
import os
import time, datetime
def hello(request):
    text = """<h1>hello my web</h1>"""
    return HttpResponse(text)

def hello2(request, number):
    text = "<h1>my number %s</h1> " % number
    return HttpResponse(text)

def hello3(request):
    return render(request, os.path.join(BASE_DIR, 'myapp/template/hello.html'), {})


def viewArticle(request, articleId):
    text = "Displaying article Number : %s"%articleId
    return HttpResponse(text)


def viewArticle(request, month, year):
    '''
    request like this: /myapp/articles/12/2006/
    then: month=12 year=2006
    see the urls.py


    :param request:
    :param month:
    :param year:
    :return:
    '''
    text = "Displaying articles of : %s/%s"%(year, month)
    return HttpResponse(text)


def mytime(request):
    return render(request, "myapp/template/second.html", {"curtime": int(time.time())})



def crudops(request):

    # delete all
    Dreamreal.objects.all().delete()

    dreamreal = Dreamreal(
        website = "www.polo.com", mail = "sorex@polo.com",
        name = "sorex", phonenumber = "002376970"
    )
    dreamreal.save()

    # read
    objs = Dreamreal.objects.all()
    res = "print <br>"
    for o in objs:
        res += o.name + "<br>"

    sorex = Dreamreal.objects.get(name="sorex")
    res += "onentitl:" + str(sorex.phonenumber)

    # delete
    sorex.delete()

    #Update
    dreamreal = Dreamreal(
        website = "www.polo.com", mail = "sorex@polo.com",
        name = "sorex", phonenumber = "002376970"
    )

    dreamreal.save()
    res += 'Updating entry<br>'

    dreamreal = Dreamreal.objects.get(name = 'sorex')
    dreamreal.name = 'thierry'
    dreamreal.save()

    return HttpResponse(res)



def login(request):
    username = "not login in"
    if request.method == "POST":
        myform = LoginForm(request.POST)
        if myform.is_valid():
            username = myform.cleaned_data["username"]
    else:
        myform = LoginForm()
    return render(request, "myapp/template/loggedin.html", {"username" : username})
