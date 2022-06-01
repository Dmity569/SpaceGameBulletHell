package com.example.spacegame.domain;

import java.util.Objects;

public class Record {

    private int id;

    private String name;

    private int score;

    public Record(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return id == record.id && score == record.score && Objects.equals(name, record.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, score);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", login='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
