package io.javabrains.coronavirustracker.controllers;

import io.javabrains.coronavirustracker.models.CountryStat;
import io.javabrains.coronavirustracker.models.DeathStats;
import io.javabrains.coronavirustracker.models.LocationStats;
import io.javabrains.coronavirustracker.models.RecoveredStats;
import io.javabrains.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        List<DeathStats> allDeaths = coronaVirusDataService.getAllDeaths();
        List<RecoveredStats> allRecovered = coronaVirusDataService.getAllRecovered();
        CountryStat turkeyCases = coronaVirusDataService.getCountryStat();
        ArrayList<CountryStat> countryStats = new ArrayList<CountryStat>();

        for(int i = 0; i < allStats.size(); i++){
            CountryStat tempStat = new CountryStat();
            tempStat.setState(allStats.get(i).getState());
            tempStat.setCountry(allStats.get(i).getCountry());
            tempStat.setLatestTotalCases(allStats.get(i).getLatestTotalCases());
            tempStat.setLatestTotalDeaths(allDeaths.get(i).getLatestTotalDeaths());
            tempStat.setDiffFromPrevDayCases(allStats.get(i).getDiffFromPrevDay());
            tempStat.setDiffFromPrevDayDeaths(allDeaths.get(i).getDiffFromPrevDay());
            countryStats.add(tempStat);
        }


        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        int totalReportedDeaths = allDeaths.stream().mapToInt(stat -> stat.getLatestTotalDeaths()).sum();
        int totalNewDeaths = allDeaths.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        int totalReportedRecovered = allRecovered.stream().mapToInt(stat -> stat.getLatestTotalRecovered()).sum();
        int totalNewRecovered = allRecovered.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();

        model.addAttribute("countryStats", countryStats);
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        model.addAttribute("locationDeaths", allDeaths);
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        model.addAttribute("totalNewDeaths", totalNewDeaths);

        model.addAttribute("locationRecovered", allRecovered);
        model.addAttribute("totalReportedRecovered", totalReportedRecovered);
        model.addAttribute("totalNewRecovered", totalNewRecovered);

        model.addAttribute("turkeyTotalCases", turkeyCases.getLatestTotalCases());
        model.addAttribute("turkeyTotalRecoveries", turkeyCases.getLatestTotalRecovered());
        model.addAttribute("turkeyTotalDeaths", turkeyCases.getLatestTotalDeaths());
        model.addAttribute("turkeyDailyCases", turkeyCases.getDiffFromPrevDayCases());
        model.addAttribute("turkeyDailyRecoveries", turkeyCases.getDiffFromPrevDayRecovered());
        model.addAttribute("turkeyDailyDeaths", turkeyCases.getDiffFromPrevDayDeaths());
        return "home";
    }
}
