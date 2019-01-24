package org.cloudbus.cloudsim.power;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.examples.power.Constants;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewHelper {



    /**
     * Creates the host list.
     *
     * @param hostsNumber the hosts number
     * @return the list< power host>
     */
    public static List<PowerHost> createHostList(int hostsNumber,int startId) {
        List<PowerHost> hostList = new ArrayList<PowerHost>();
        for (int i = 0; i < hostsNumber; i++) {
            int hostType = Constants.HOST_TYPES -1;

            List<Pe> peList = new ArrayList<Pe>();
            for (int j = 0; j < Constants.HOST_PES[hostType]; j++) {
                peList.add(new Pe(j, new PeProvisionerSimple(Constants.HOST_MIPS[hostType])));
            }

            hostList.add(new PowerHost(
                    i+startId,
                    new RamProvisionerSimple(Constants.HOST_RAM[hostType]),
                    new BwProvisionerSimple(Constants.HOST_BW),
                    Constants.HOST_STORAGE,
                    peList,
                    new VmSchedulerTimeSharedOverSubscription(peList),
                    Constants.HOST_POWER[hostType]));
        }
        return hostList;
    }


    /** Crete DataCenter
     * @param name
     * @param datacenterClass
     * @param hostList
     * @param vmAllocationPolicy
     * @return
     * @throws Exception
     */
    public static Datacenter createDatacenter(String name, Class<? extends Datacenter> datacenterClass, List<PowerHost> hostList, VmAllocationPolicy vmAllocationPolicy) throws Exception {
        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this resource
        double costPerBw = 0.0; // the cost of using bw in this resource

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch,
                os,
                vmm,
                hostList,
                time_zone,
                cost,
                costPerMem,
                costPerStorage,
                costPerBw);

        Datacenter datacenter = null;
        try {
            datacenter = datacenterClass.getConstructor(
                    String.class,
                    DatacenterCharacteristics.class,
                    VmAllocationPolicy.class,
                    List.class,
                    Double.TYPE).newInstance(
                    name,
                    characteristics,
                    vmAllocationPolicy,
                    new LinkedList<Storage>(),
                    Constants.SCHEDULING_INTERVAL);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        return datacenter;
    }


}
