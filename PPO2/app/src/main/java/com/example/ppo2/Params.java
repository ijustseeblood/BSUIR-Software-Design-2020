package com.example.ppo2;

public class Params
{
    public int id;
    public int color;
    public String title;
    public int prepare;
    public int work;
    public int chill;
    public int cycle;
    public int sets;
    public int setChill;

    public Params(int id, int color, String title, int prepare, int work, int chill, int cycles, int sets, int setChill)
    {
        this.id = id;
        this.color = color;
        this.title = title;
        this.prepare = prepare;
        this.work = work;
        this.chill = chill;
        this.cycle = cycles;
        this.sets = sets;
        this.setChill = setChill;
    }

    @Override
    public String toString() {
        return this.title;
    }
}