package com.example.selfish.persistence;

import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.selfish.persistence.entities.Idea;
import com.example.selfish.persistence.services.IdeaService;
import com.example.selfish.persistence.services.PersonService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    @Autowired
    private PersonService people;
    @Autowired
    private IdeaService ideas;

    Logger log = org.slf4j.LoggerFactory.getLogger(BootstrapData.class);

    @Override
    @Transactional
    public void run(String... args) {
        /* Create people */
        ideas.deleteAll();
        people.deleteAll();
        
        


        var drmike = people.create("Dr. Mike;Israetel");
        var karpathy = people.create("Dr. Andrei;Karpathy");
        var fujoe = people.create("Joe;Hudson");
        var jdfm = people.create("James Donald Forbes;McCann");
        var navalravikant = people.create("Naval;Ravikant");
        var camus = people.create("Albert;Camus");
        var einstein = people.create("Albert;Einstein");
        var sommerfeld = people.create("Arnold;Sommerfeld");
        var arnie = people.create("Arnold;Schwarzenegger");

        var volumetraininggood = ideas.create("Volume training is good", "Volume training is good for hypertrophy", Set.of(drmike, arnie));
        var relativity = ideas.create("Theory of relativity", "The theory of relativity is a scientific theory of gravitation", Set.of(einstein, sommerfeld));

        var holisticSelfishness = ideas.create("Holistic selfishness", "Holistic selfishness is a philosophy that emphasizes the importance of self-interest in achieving personal and societal well-being", Set.of(navalravikant, fujoe));
        var existenceBad = ideas.create("Life is not inherently good", "Existen does not have an inherent meaning or purpose, it is up to each individual to create their own meaning and purpose in life", Set.of(camus, jdfm));

        var agiWillbeBenevolent = ideas.create("AGI will be benevolent", "AGI will be benevolent and will not destroy humanity", Set.of(karpathy, drmike));

        var agiMayKillUs = ideas.create("AGI may kill us", "AGI may kill us if we do not take precautions");

        ideas.addAuthors(agiMayKillUs, Set.of(jdfm, camus));

        // create contradictions 
        ideas.addContradiction((Idea)agiWillbeBenevolent, (Idea)agiMayKillUs);
    }
 
}