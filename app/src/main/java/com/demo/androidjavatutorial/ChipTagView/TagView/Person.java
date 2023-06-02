package com.demo.androidjavatutorial.ChipTagView.TagView;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;

    public Person(String n) {
        name = n;
    }

    public static Person[] samplePeople() {
        return new Person[]{
                new Person("Playing"),
                new Person("Reading"),
                new Person("Android"),
                new Person("Angular"),
                new Person("UI-UX"),
                new Person("Graphic"),
                new Person("Photoshop"),
                new Person("Full Stack"),
                new Person("Web Designer"),
                new Person("Web Development"),
                new Person("JavaScript"),
                new Person("Python"),
                new Person("Java"),
                new Person("C"),
                new Person("C++"),
                new Person("Ios"),
                new Person("Flutter"),
                new Person("Backend"),
                new Person("React"),
                new Person("Ionic"),
                new Person("Running"),
                new Person("Learning"),
                new Person("Drawing"),
                new Person("Singing")
        };
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    private Person(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}