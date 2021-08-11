package com.example.demo.customRepositories;

public interface TroubleMachineCustomRepository<T> {

	boolean checkIfMachineIsTentativelyInTrouble(String machinecode, String machineNumber);
	
	boolean checkIfMachineIsInTroubled(String machinecode, String machineNumber);
	
}
