/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


package net.mahdirazavi;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.examples.power.Constants;
import org.cloudbus.cloudsim.examples.power.Helper;
import org.cloudbus.cloudsim.examples.power.random.RandomHelper;
import org.cloudbus.cloudsim.power.*;

import java.util.Calendar;
import java.util.List;


public class PowerExample {





    ////////////////////////// STATIC METHODS ///////////////////////


    public static void testSimple() {
        String EXPERIMENT_NAME = "PowerTestSimple";
        Log.printLine("\n\n====================================================\nStarting " + EXPERIMENT_NAME + "....");
        Log.disable();

        try {
            // First step: Initialize the CloudSim package.
            int num_user = 1;   // number of grid users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events


            //############ Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);


            //############ Second step: Create Datacenters
            List<PowerHost> hostList = NewHelper.createHostList(MyConst.NUMBER_OF_HOSTS,0);
            PowerDatacenterNonPowerAware datacenter = (PowerDatacenterNonPowerAware) NewHelper.createDatacenter(
                    "Datacenter",
                    PowerDatacenterNonPowerAware.class,
                    hostList,
                    new PowerVmAllocationPolicySimple2(hostList));

            datacenter.setDisableMigrations(true);


            //############ Third step: Create Broker
            DatacenterBroker broker = Helper.createBroker();
            int brokerId = broker.getId();


            //############ Fourth step: Create VMs and Cloudlets and send them to broker
            List<Cloudlet> cloudletList = RandomHelper.createCloudletList(brokerId, MyConst.NUMBER_OF_VMS);
            List<Vm> vmList = Helper.createVmList(brokerId, cloudletList.size());

            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);


            //############ Fifth step: Starts the simulation
            CloudSim.terminateSimulation(Constants.SIMULATION_LIMIT);
            double lastClock = CloudSim.startSimulation();

            Log.enable();

            List<Cloudlet> newList = broker.getCloudletReceivedList();
//            Log.printLine("Received " + newList.size() + " cloudlets");

            CloudSim.stopSimulation();


            //############ Final step: Print results when simulation is over
            Helper.printResults(datacenter, vmList, lastClock, EXPERIMENT_NAME, Constants.OUTPUT_CSV, "/logs");

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
            System.exit(0);
        }
        Log.enable();
        Log.printLine("Finished " + EXPERIMENT_NAME);
    }

    public static void testGreedy() {
        String EXPERIMENT_NAME = "PowerTestGreedy";
        Log.printLine("\n\n====================================================\nStarting " + EXPERIMENT_NAME + "....");
        Log.disable();

        try {
            // First step: Initialize the CloudSim package.
            int num_user = 1;   // number of grid users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;  // mean trace events


            //############ Initialize the CloudSim library
            CloudSim.init(num_user, calendar, trace_flag);


            //############ Second step: Create Datacenters
            List<PowerHost> hostList = NewHelper.createHostList(MyConst.NUMBER_OF_HOSTS,0);
            PowerDatacenterNonPowerAware datacenter = (PowerDatacenterNonPowerAware) NewHelper.createDatacenter(
                    "Datacenter",
                    PowerDatacenterNonPowerAware.class,
                    hostList,
                    new PowerVmAllocationPolicyGreedy(hostList));

            datacenter.setDisableMigrations(true);


            //############ Third step: Create Broker
            DatacenterBroker broker = Helper.createBroker();
            int brokerId = broker.getId();


            //############ Fourth step: Create VMs and Cloudlets and send them to broker
            List<Cloudlet> cloudletList = RandomHelper.createCloudletList(brokerId, MyConst.NUMBER_OF_VMS);
            List<Vm> vmList = Helper.createVmList(brokerId, cloudletList.size());

            broker.submitVmList(vmList);
            broker.submitCloudletList(cloudletList);


            //############ Fifth step: Starts the simulation
            CloudSim.terminateSimulation(Constants.SIMULATION_LIMIT);
            double lastClock = CloudSim.startSimulation();

            Log.enable();

            List<Cloudlet> newList = broker.getCloudletReceivedList();
//            Log.printLine("Received " + newList.size() + " cloudlets");

            CloudSim.stopSimulation();


            //############ Final step: Print results when simulation is over
            Helper.printResults(datacenter, vmList, lastClock, EXPERIMENT_NAME, Constants.OUTPUT_CSV, "/logs");

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("The simulation has been terminated due to an unexpected error");
            System.exit(0);
        }

        Log.printLine("Finished " + EXPERIMENT_NAME);
    }

    /**
     * Creates main() to run this example
     */
    public static void main(String[] args) {

        testSimple();

        testGreedy();

    }
}
