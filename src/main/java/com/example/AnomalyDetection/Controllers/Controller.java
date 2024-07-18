package com.example.AnomalyDetection.Controllers;

import com.example.AnomalyDetection.KafkaProducer.KProducer;
import com.example.AnomalyDetection.RepeatingPattern.CheckRepitition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
    @Autowired
    KProducer kProducer;

    @GetMapping(path = "/")
    public String home() {
        return "<h1>Welcome</h1>";
    }

    @GetMapping(path = "/MostOccurringPattern/{beginTimeStamp}/{endTimeStamp}")
    public String getMostOccurringPattern(@PathVariable String beginTimeStamp, @PathVariable String endTimeStamp) {
        return CheckRepitition.getTheMostOccurringPattern(beginTimeStamp, endTimeStamp);
    }

    @GetMapping(path = "/MostOccurringPatternExcluding/{beginTimeStamp}/{endTimeStamp}/{excludeList}")
    public String getMostOccurringPatternExcluding(@PathVariable String beginTimeStamp, @PathVariable String endTimeStamp, @PathVariable String[] excludeList) {
        return CheckRepitition.getTheMostOccurringPattern(beginTimeStamp, endTimeStamp, excludeList);
    }

    @GetMapping(path = "/ListMostOccurringPatterns/{beginTimeStamp}/{endTimeStamp}")
    public String getListMostOccurringPatterns(@PathVariable String beginTimeStamp, @PathVariable String endTimeStamp) {
        kProducer.send(2, beginTimeStamp, endTimeStamp);
        return "request queued , results will be updated in clustering results";

    }

    @GetMapping(path = "/AnomalyDetection/{beginTimeStamp}/{endTimeStamp}")
    public String getAnomalyDetectionResults(@PathVariable String beginTimeStamp, @PathVariable String endTimeStamp) {
        kProducer.send(1, beginTimeStamp, endTimeStamp);
        return "request queued , results will be updated in anomaly detection results";
    }


}
