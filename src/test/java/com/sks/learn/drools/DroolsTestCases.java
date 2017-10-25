package com.sks.learn.drools;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import com.sks.learn.model.Product;

public class DroolsTestCases {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("################################ START :: BRMS-DROOLS ################################");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("################################ END :: BRMS-DROOLS ################################");
	}

	@Test
	public void testDrools() {
		System.out.println("Inside testDrools()");
		// LOAD THE RULES
		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		KieFileSystem kfs = ks.newKieFileSystem();
		kfs.write(ResourceFactory.newClassPathResource("/Users/ssing69/ACTSOME/EclipseNeonWS/learn-drools/src/main/resources/com/sks/learn/rule/Rules.drl", this.getClass()));

		KieBuilder kb = ks.newKieBuilder(kfs);

		kb.buildAll(); // kieModule is automatically deployed to KieRepository
						// if successfully built.
		if (kb.getResults().hasMessages(Message.Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
		}

		KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());

		KieSession kSession = kContainer.newKieSession();

		// SET THE FACTS
		Product product = new Product();
		product.setType("gold");

		System.out.println("Fire All Rules...");
		kSession.fireAllRules();
		kSession.dispose();

		System.out.println("The discount for the product " + product.getType() + " is " + product.getDiscount());

	}

}
