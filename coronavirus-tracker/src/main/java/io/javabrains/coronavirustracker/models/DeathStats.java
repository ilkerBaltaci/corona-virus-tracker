package io.javabrains.coronavirustracker.models;

public class DeathStats {
    private String state;
    private String country;
    private int latestTotalDeaths;
    private int diffFromPrevDay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalDeaths() {
        return latestTotalDeaths;
    }

    public void setLatestTotalDeaths(int latestTotalDeaths) {
        this.latestTotalDeaths = latestTotalDeaths;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    @Override
    public String toString() {
        return "DeathStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalDeaths=" + latestTotalDeaths +
                ", diffFromPrevDay=" + diffFromPrevDay +
                '}';
    }
}
