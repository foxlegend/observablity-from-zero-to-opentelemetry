from rest_framework import serializers
from rest_framework import viewsets

from vets.models import Specialty, Vet

# Create your views here.
class SpecialitySerializer(serializers.ModelSerializer):
    class Meta:
        model = Specialty
        fields = ['id', 'name']

class VetSerializer(serializers.ModelSerializer):
    specialties = SpecialitySerializer(many=True, read_only=True)
    class Meta:
        model = Vet
        fields = ['id', 'first_name', 'last_name', 'specialties', 'nr_of_specialties']

class VetsViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Vet.objects.all()
    serializer_class = VetSerializer