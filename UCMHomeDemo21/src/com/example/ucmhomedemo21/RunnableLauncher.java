package com.example.ucmhomedemo21;


public class RunnableLauncher implements Runnable {

	private LaunchController launchController;
	
	public RunnableLauncher(LaunchController launchController) {
		this.launchController = launchController;
	}

	@Override
	public void run() {
		if (this.launchController != null) {
			this.launchController.onFinishLaunching();
		}
	}

}
