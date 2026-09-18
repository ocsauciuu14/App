package com.example.myapplication;

class Student {
    private String Id;
    private String Name;
    private Integer Age;
    private Float Gpa;

    public  Student(String Id , String Name , Integer Age, Float Gpa){
        this.Id =Id;
        this.Name = Name;
        this.Age = Age;
        this.Gpa = Gpa;
    }
    public String GetId(){
        return Id;
    }
    public String GetName(){
        return Name;
    }
    public Integer GetAge(){
        return Age;
    }
    public Float GetGpa(){
        return Gpa;
    }
    public String displayInfo(){
        return GetId()+" " + GetName()+" " + GetAge() +" "+ GetGpa();
    }
    public String getRank(){
        if(Gpa >= 8.5){
            return "Giỏi";
        }
        else if(Gpa >= 7.0){
            return "Khá";
        }
        else if(Gpa >= 5.0){
            return "Trung bình";
        }
        else{
            return "Yếu";
        }
    }
}