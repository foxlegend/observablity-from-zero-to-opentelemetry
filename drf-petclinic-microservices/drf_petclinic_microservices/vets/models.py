from django.db import models

# Create your models here.
class Specialty(models.Model):
    name = models.CharField(blank=False, max_length=256)

class Vet(models.Model):
    first_name = models.CharField(blank=False, max_length=256)
    last_name = models.CharField(blank=False, max_length=256)
    specialties = models.ManyToManyField("Specialty", related_name="vets")
    
    def nr_of_specialties(self):
        return self.specialties.count()