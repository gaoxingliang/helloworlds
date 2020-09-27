from django.db import models

# Create your models here.

class Dreamreal(models.Model):
    website = models.CharField(max_length=100)
    mail = models.CharField(max_length=100)
    name = models.CharField(max_length=100)
    phonenumber = models.IntegerField()


    class Meta:
        db_table = "dreamreal"