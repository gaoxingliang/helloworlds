"""myproject URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    url(r'^hello/', 'myapp.views.hello', name='sayhello'),
    url(r'^hello2/', 'myapp.views.hello2', name='sayhello2'),
    url(r'^hello3/', 'myapp.views.hello3', name='sayhello3'),
    url(r'^mytime/', 'myapp.views.mytime', name="mytime"),
    url(r'crud/', 'myapp.views.crudops'),
    url(r'^article/(\d+)/', 'myapp.views.viewArticle', name = 'article'),
    url(r'^articles/(\d{2})/(\d{4})', 'myapp.views.viewArticles', name = 'articles')
]
