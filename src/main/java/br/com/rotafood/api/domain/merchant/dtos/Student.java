package br.com.rotafood.api.domain.merchant.dtos;

record Student(String name, int rollNo, int marks) {
    public Student {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }
}