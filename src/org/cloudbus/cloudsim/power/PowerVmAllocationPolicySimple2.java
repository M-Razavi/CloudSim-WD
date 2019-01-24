/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

/**
 * A simple VM allocation policy that does <b>not</b> perform any optimization on VM allocation.
 *
 * <br/>If you are using any algorithms, policies or workload included in the power package please cite
 * the following paper:<br/>
 *
 * <ul>
 * <li><a href="http://dx.doi.org/10.1002/cpe.1867">Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience (CCPE), Volume 24,
 * Issue 13, Pages: 1397-1420, John Wiley & Sons, Ltd, New York, USA, 2012</a>
 * </ul>
 *
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 3.0
 */
public class PowerVmAllocationPolicySimple2 extends PowerVmAllocationPolicyAbstract {

    /**
     * Instantiates a new PowerVmAllocationPolicySimple.
     *
     * @param list the list
     */
    public PowerVmAllocationPolicySimple2(List<? extends Host> list) {
        super(list);
    }

    @Override
    public boolean allocateHostForVm(Vm vm, Host host) {
        Log.enable();
        if (host == null) {
            Log.formatLine("%.2f: No suitable host found for VM #" + vm.getId() + "\n", CloudSim.clock());
            Log.disable();
            return false;
        }
        if (host.vmCreate(vm)) { // if vm has been succesfully created in the host
            getVmTable().put(vm.getUid(), host);
            Log.formatLine(
                    "%.2f: VM #" + vm.getId() + " has been allocated to the host #" + host.getId(),
                    CloudSim.clock());
            Log.disable();
            return true;
        }
        Log.formatLine(
                "%.2f: Creation of VM #" + vm.getId() + " on the host #" + host.getId() + " failed\n",
                CloudSim.clock());

        Log.disable();
        return false;
    }

    /**
     * The method doesn't perform any VM allocation optimization
     * and in fact has no effect.
     * @param vmList
     * @return
     */
    @Override
    public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
        //@todo It is better to return an empty map in order to avoid NullPointerException or extra null checks
        // This policy does not optimize the VM allocation
        return null;
    }

}
