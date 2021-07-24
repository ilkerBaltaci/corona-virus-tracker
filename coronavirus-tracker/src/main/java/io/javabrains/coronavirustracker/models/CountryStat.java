package io.javabrains.coronavirustracker.models;

public class CountryStat {
    private String State;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDayCases;
    private int latestTotalDeaths;
    private int diffFromPrevDayDeaths;
    private int latestTotalRecovered;
    private int diffFromPrevDayRecovered;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int getDiffFromPrevDayCases() {
        return diffFromPrevDayCases;
    }

    public void setDiffFromPrevDayCases(int diffFromPrevDayCases) {
        this.diffFromPrevDayCases = diffFromPrevDayCases;
    }

    public int getLatestTotalDeaths() {
        return latestTotalDeaths;
    }

    public void setLatestTotalDeaths(int latestTotalDeaths) {
        this.latestTotalDeaths = latestTotalDeaths;
    }

    public int getDiffFromPrevDayDeaths() {
        return diffFromPrevDayDeaths;
    }

    public void setDiffFromPrevDayDeaths(int diffFromPrevDayDeaths) {
        this.diffFromPrevDayDeaths = diffFromPrevDayDeaths;
    }

    public int getLatestTotalRecovered() {
        return latestTotalRecovered;
    }

    public void setLatestTotalRecovered(int latestTotalRecovered) {
        this.latestTotalRecovered = latestTotalRecovered;
    }

    public int getDiffFromPrevDayRecovered() {
        return diffFromPrevDayRecovered;
    }

    public void setDiffFromPrevDayRecovered(int diffFromPrevDayRecovered) {
        this.diffFromPrevDayRecovered = diffFromPrevDayRecovered;
    }
}
