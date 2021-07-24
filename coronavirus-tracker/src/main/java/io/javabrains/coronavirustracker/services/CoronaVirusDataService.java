package io.javabrains.coronavirustracker.services;

import io.javabrains.coronavirustracker.models.CountryStat;
import io.javabrains.coronavirustracker.models.DeathStats;
import io.javabrains.coronavirustracker.models.LocationStats;
import io.javabrains.coronavirustracker.models.RecoveredStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String VIRUS_DEATH_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static String VIRUS_RECOVERED_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();
    private List<DeathStats> allDeaths = new ArrayList<>();
    private List<RecoveredStats> allRecovered = new ArrayList<>();
    private CountryStat countryStat = new CountryStat();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public List<DeathStats> getAllDeaths() {
        return allDeaths;
    }

    public List<RecoveredStats> getAllRecovered() {
        return allRecovered;
    }

    public CountryStat getCountryStat() { return countryStat; }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        List<DeathStats> newDeaths = new ArrayList<>();
        List<RecoveredStats> newRecovered = new ArrayList<>();

        //Request for Confirmed Datas
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> recordsOfStats = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        //Request for Death Datas
        request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DEATH_URL)).build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> recordsOfDeaths = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        //Request for Recovered Datas
        request = HttpRequest.newBuilder().uri(URI.create(VIRUS_RECOVERED_URL)).build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> recordsOfRecovered = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : recordsOfStats) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases-prevDayCases);
            if("Turkey".equals(locationStat.getCountry())){
                countryStat.setCountry(locationStat.getCountry());
                countryStat.setDiffFromPrevDayCases(locationStat.getDiffFromPrevDay());
                countryStat.setLatestTotalCases(locationStat.getLatestTotalCases());
            }
            newStats.add(locationStat);
        }
        this.allStats = newStats;

        for (CSVRecord record : recordsOfDeaths) {
            DeathStats deathStats = new DeathStats();
            deathStats.setState(record.get("Province/State"));
            deathStats.setCountry(record.get("Country/Region"));
            int latestDeaths = Integer.parseInt(record.get(record.size() - 1));
            int prevDayDeaths = Integer.parseInt(record.get(record.size() - 2));
            deathStats.setLatestTotalDeaths(latestDeaths);
            deathStats.setDiffFromPrevDay(latestDeaths-prevDayDeaths);
            if("Turkey".equals(deathStats.getCountry())){
                this.countryStat.setDiffFromPrevDayDeaths(deathStats.getDiffFromPrevDay());
                this.countryStat.setLatestTotalDeaths(deathStats.getLatestTotalDeaths());
            }
            newDeaths.add(deathStats);
        }
        this.allDeaths = newDeaths;

        for (CSVRecord record : recordsOfRecovered) {
            RecoveredStats recoveredStats = new RecoveredStats();
            recoveredStats.setState(record.get("Province/State"));
            recoveredStats.setCountry(record.get("Country/Region"));
            int latestRecovered = Integer.parseInt(record.get(record.size() - 1));
            int prevDayRecovered = Integer.parseInt(record.get(record.size() - 2));
            recoveredStats.setLatestTotalRecovered(latestRecovered);
            recoveredStats.setDiffFromPrevDay(latestRecovered-prevDayRecovered);
            if("Turkey".equals(recoveredStats.getCountry())){
                countryStat.setDiffFromPrevDayRecovered(recoveredStats.getDiffFromPrevDay());
                countryStat.setLatestTotalRecovered(recoveredStats.getLatestTotalRecovered());
            }
            newRecovered.add(recoveredStats);
        }
        this.allRecovered = newRecovered;


    }
}
