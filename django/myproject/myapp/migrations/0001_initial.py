# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Dreamreal',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('website', models.CharField(max_length=100)),
                ('mail', models.CharField(max_length=100)),
                ('name', models.CharField(max_length=100)),
                ('phonenumber', models.IntegerField()),
            ],
            options={
                'db_table': 'dreamreal',
            },
        ),
    ]
