//package org.usfirst.frc.team246.robot.overclockedLibraries;
//
//import java.util.ArrayList;
//
//import edu.wpi.first.wpilibj.buttons.Trigger;
//import edu.wpi.first.wpilibj.command.Command;
//
//
///**
// * An alternative method to the WPILIB's CommandGroup that is more difficult to use but is much more 
// * capable of doing complex tasks, decision making, and repetition.
// * 
// * Rather than using CommandGroup's method of indexing the entire sequence of commands inside of the 
// * constructor and then running through that sequence later on in compliance with the scheduler, 
// * CommandGroupPlus will call a method that teams must override, sequence(), once in real-time when the 
// * command is initialized. This allows for standard coding such as if statements, while loops, and even 
// * subsystem method calls to be interspersed within the sequence of commands.
// * 
// * In order to call a command within sequence(), use the call() method with the desired command to call as 
// * the first parameter. The call method offers utilites to wait for other certain events before starting 
// * the command, and will hold up the thread until that happens.
// * 
// * The CommandGroupPlus class contains a variable, lastCommand, which is the last command to have been 
// * input into the call() method. Use it as the second parameter for call() to achieve a similar effect to 
// * CommandGroup's addSequential().
// * 
// * The callWhen() method functions similarly to the call method, except it will not hold up the thread and 
// * allow other commands to be started before it does.
// * 
// * The waitFor() method offers the same utilities for holding up the thread that call() does, but will not 
// * call a command when it is finished.
// * 
// * In order to ensure that this command will stop running when it is either finished or interrupted, the
// * checkForInterruption() method must be called frequently throughout the sequence() method. This is done
// * automatically in the call(), callWhen(), and waitFor() methods, but if your code has to potential to 
// * run for long periods of time without any of these methods being called, you should strongly consider
// * periodically calling checkForInterruption() yourself.
// * 
// * One final note: unlike a CommandGroup, CommandGroupPlus does not "require" all of the subsystems that it 
// * uses, however each of the individual commands inside of it sill have their normal requirements.
// */
//public abstract class CommandGroupPlus extends Command{
//	
//	/**
//	 * The most recent command to be run by the call() method. Use this as a parameter for the call(),
//	 * callWhen(), and waitFor() methods to achieve a similar effect to CommandGroups addSequential().
//	 */
//	public Command lastCommand;
//	
//	private ArrayList<Command> commandsStarted; // all of the commands which this CommandGroupPlus has started
//	
//	private ArrayList<QueuedCommand> commandsInQueue; // the commands that have been queued by callWhen() but have not yet been run
//	
//	private Sequencer sequencer;
//	private Thread sequencerThread;
//	
//	public CommandGroupPlus()
//	{
//		commandsStarted = new ArrayList<Command>();
//		sequencer = new Sequencer();
//		sequencerThread = new Thread(sequencer);
//	}
//	
//	@Override
//	/**
//     * The initialize method is called the first time this Command is run after being started. Because the 
//     * sequencing thread is started in this method, please call super.initialize() at some point during your 
//     * code if you are overriding
//     */
//	protected void initialize() {
//		sequencerThread.start();
//	}
//
//	@Override
//	/**
//	 * The execute method is called repeatedly until this Command either finishes or is canceled. Because
//	 * the queue for callWhen() is iterated through in this method, please call super.execute() at some point
//	 * during your code if you are overriding.
//	 */
//	protected void execute() {
//		for(int i = 0; i < commandsInQueue.size();)
//		{
//			Command command = commandsInQueue.get(i).getCommand();
//			if(commandsInQueue.get(i).isReadyToRun())
//			{
//				command.start();
//				commandsStarted.add(command);
//				commandsInQueue.remove(i);
//			}
//			else i++;
//		}
//	}
//
//	@Override
//	/**
//	 * Returns whether this command is finished. If it is, then the command will be removed and 
//	 * {@link CommandGroupPlus#end() end()} will be called. If overriding, please note that this method is
//	 * what causes the command to finish after the sequence() method has finished. Include super.isFinished()
//	 * in your code if you want to utilize this feature.
//     *
//     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()} method
//     * for time-sensitive commands.</p>
//     * @return whether this command is finished.
//     * @see Command#isTimedOut() isTimedOut()
//	 */
//	protected boolean isFinished() {
//		return sequencer.isFinished();
//	}
//
//	@Override
//	/**
//	 * Called when the command ended peacefully.  This is where you may want to wrap up loose ends, like 
//	 * shutting off a motor that was being used in the command. Because code in this method ensures that 
//	 * the sequencer stops running, please call super.end() at some point during your code if you are 
//	 * overriding.
//	 */
//	protected void end() {
//		sequencerThread.interrupt();
//	}
//
//	@Override
//	/**
//	 * Called when the command ends because somebody called {@link Command#cancel() cancel()} or another 
//	 * command shared the same requirements as this one, and booted it out.
//     *
//     * <p>This is where you may want
//     * to wrap up loose ends, like shutting off a motor that was being used
//     * in the command.</p>
//     *
//     * <p>Generally, it is useful to simply call the {@link Command#end() end()} method
//     * within this method</p>
//     * 
//     * Because code in this method ensures that the sequencer stops running, please call super.end() at 
//     * some point during your code if you are overriding.
//     */
//	protected void interrupted() {
//		sequencerThread.interrupt();
//	}
//	
//	/**
//	 * This is where all of the code for the sequence of commands should go. This method is run once 
//	 * through in its own thread. Use call(), callWhen(), or waitFor() to start commands in the desired 
//	 * sequence. 
//	 * 
//	 * Unlike in a CommandGroup, this method will be run in real-time, so conditional statements looking 
//	 * at the current state of the robot, extraneous lines of code interspersed throughout this method, and 
//	 * all other things of that nature will function properly. 
//	 * 
//	 * In order for the sequence method to stop if the command is interrupted, checkForInterruption() needs 
//	 * to be called frequently throughout this method. checkForInterruption() is called automatically in 
//	 * call(), callWhen(), and waitFor(), but if there is potential for a long period of time without any 
//	 * of those methods being called, it is strongly encouraged that you manually call checkForException. 
//	 * Usage of system calls that throw an InterruptedException, such as Thread.sleep(), can be used in 
//	 * place of checkForInterruption().
//	 * 
//	 * @throws InterruptedException
//	 */
//	protected abstract void sequence() throws InterruptedException;
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for every command in 
//	 * commandsToWaitFor to be finished and the get() method of every trigger in triggersToWaitFor to 
//	 * return true before starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If a command in commandsToWaitFor stops 
//	 * running, but then gets restarted, it will still count as being finished. If a trigger returns true 
//	 * momentarily, but then becomes false again, it will still count as true.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandsToWaitFor The commands that must all, at some point in time, finish before 
//	 * commandToRun is allowed to run and this method finishes.
//	 * @param triggersToWaitFor The triggers that must all, at some point in time, evaluate to true before
//	 * commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command[] commandsToWaitFor, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		checkForInterruption();
//		
//		waitFor(commandsToWaitFor, triggersToWaitFor);
//		
//		commandToRun.start();
//		
//		lastCommand = commandToRun;
//		commandsStarted.add(commandToRun);
//		
//		checkForInterruption();
//		
//		return commandToRun;
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun) throws InterruptedException
//	{
//		return call(commandToRun, new Command[0], new Trigger[0]);
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for commandToWaitFor to 
//	 * be finished before starting commandToRun. 
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandToWaitFor The command that must finish before  commandToRun is allowed to run and this 
//	 * method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command commandToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, new Command[]{commandToWaitFor}, new Trigger[0]);
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for every command in 
//	 * commandsToWaitFor to be finished before starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If a command in commandsToWaitFor stops 
//	 * running, but then gets restarted, it will still count as being finished.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandsToWaitFor The commands that must all, at some point in time, finish before 
//	 * commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command[] commandsToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, commandsToWaitFor, new Trigger[0]);
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for the get() method of 
//	 * triggerToWaitFor to return true before starting commandToRun. 
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param triggerToWaitFor The trigger that must evaluate to true before commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, new Command[0], new Trigger[]{triggerToWaitFor});
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for  the get() method of 
//	 * every trigger in triggersToWaitFor to return true before starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If a trigger returns true momentarily, 
//	 * but then becomes false again, it will still count as true.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param triggersToWaitFor The triggers that must all, at some point in time, evaluate to true before
//	 * commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, new Command[0], triggersToWaitFor);
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for commandToWaitFor to 
//	 * be finished and the get() method of triggerToWaitFor to return true before starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If commandToWaitFor stops 
//	 * running, but then gets restarted, it will still count as being finished. If triggerToWaitFor returns
//	 * true momentarily, but then becomes false again, it will still count as true.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandToWaitFor The command that must finish before commandToRun is allowed to run and 
//	 * this method finishes.
//	 * @param triggerToWaitFor The trigger that must evaluate to true before commandToRun is allowed to 
//	 * run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command commandToWaitFor, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, new Command[]{commandToWaitFor}, new Trigger[]{triggerToWaitFor});
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for every command in 
//	 * commandsToWaitFor to be finished and the get() method of triggerToWaitFor to return true before 
//	 * starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If a command in commandsToWaitFor stops 
//	 * running, but then gets restarted, it will still count as being finished. If triggerToWaitFor returns 
//	 * true momentarily, but then becomes false again, it will still count as true.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandsToWaitFor The commands that must all, at some point in time, finish before 
//	 * commandToRun is allowed to run and this method finishes.
//	 * @param triggerToWaitFor The trigger that must evaluate to true before
//	 * commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command[] commandsToWaitFor, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, commandsToWaitFor, new Trigger[]{triggerToWaitFor});
//	}
//	
//	/**
//	 * This method is to be used within sequence() to call commands. It will wait for commandToWaitFor to 
//	 * be finished and the get() method of every trigger in triggersToWaitFor to  return true before 
//	 * starting commandToRun. 
//	 * 
//	 * The waiting conditions do not need to occur simultaneously. If commandToWaitFor stops running, but 
//	 * then gets restarted, it will still count as being finished. If a trigger returns true momentarily, 
//	 * but then becomes false again, it will still count as true.
//	 * 
//	 * Once commandToRun is started, it will be put inside the lastCommand variable.
//	 * 
//	 * This method continuously checks to see if the sequence should end.
//	 * 
//	 * @param commandToRun The command that will be started after waiting.
//	 * @param commandToWaitFor The command that must finish before commandToRun is allowed to run and 
//	 * this method finishes.
//	 * @param triggersToWaitFor The triggers that must all, at some point in time, evaluate to true before
//	 * commandToRun is allowed to run and this method finishes.
//	 * @return commandToRun
//	 * @throws InterruptedException
//	 */
//	protected Command call(Command commandToRun, Command commandToWaitFor, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		return call(commandToRun, new Command[]{commandToWaitFor}, triggersToWaitFor);
//	}
//	
//	protected Command callWhen(Command commandToRun, Command[] commandsToWaitFor, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		checkForInterruption();
//		
//		commandsInQueue.add(new QueuedCommand(commandToRun, commandsToWaitFor, triggersToWaitFor));
//		
//		checkForInterruption();
//		
//		return commandToRun;
//	}
//	
//	protected Command callWhen(Command commandToRun) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[0], new Trigger[0]);
//	}
//	protected Command callWhen(Command commandToRun, Command commandToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[]{commandToWaitFor}, new Trigger[0]);
//	}
//	protected Command callWhen(Command commandToRun, Command[] commandsToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, commandsToWaitFor, new Trigger[0]);
//	}
//	protected Command callWhen(Command commandToRun, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[0], new Trigger[]{triggerToWaitFor});
//	}
//	protected Command callWhen(Command commandToRun, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[0], triggersToWaitFor);
//	}
//	protected Command callWhen(Command commandToRun, Command commandToWaitFor, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[]{commandToWaitFor}, new Trigger[]{triggerToWaitFor});
//	}
//	protected Command callWhen(Command commandToRun, Command[] commandsToWaitFor, Trigger triggerToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, commandsToWaitFor, new Trigger[]{triggerToWaitFor});
//	}
//	protected Command callWhen(Command commandToRun, Command commandToWaitFor, Trigger[] triggersToWaitFor) throws InterruptedException
//	{
//		return callWhen(commandToRun, new Command[]{commandToWaitFor}, triggersToWaitFor);
//	}
//	
//	protected void waitFor(Command[] commands, Trigger[] triggers) throws InterruptedException
//	{
//		boolean[] pastCommandResults = generatePastCommandResults(commands);
//		boolean[] pastTriggerResults = generatePastTriggerResults(triggers);
//		
//		while(!isReadyToRun(commands, pastCommandResults, triggers, pastTriggerResults)) Thread.sleep(5);
//	}
//	
//	protected void waitFor(Command command) throws InterruptedException
//	{
//		waitFor(new Command[]{command}, new Trigger[0]);
//	}
//	protected void waitFor(Command[] commands) throws InterruptedException
//	{
//		waitFor(commands, new Trigger[0]);
//	}
//	protected void waitFor(Trigger trigger) throws InterruptedException
//	{
//		waitFor(new Command[0], new Trigger[]{trigger});
//	}
//	protected void waitFor(Trigger[] triggers) throws InterruptedException
//	{
//		waitFor(new Command[0], triggers);
//	}
//	protected void waitFor(Command[] commands, Trigger trigger) throws InterruptedException
//	{
//		waitFor(commands, new Trigger[]{trigger});
//	}
//	protected void waitFor(Command command, Trigger[] triggers) throws InterruptedException
//	{
//		waitFor(new Command[]{command}, triggers);
//	}
//	
//	private static boolean isReadyToRun(Command[] commands, boolean[] pastCommandResults, Trigger[] triggers, boolean[] pastTriggerResults)
//	{
//		for(int i = 0; i < commands.length; i++)
//		{
//			if(!commands[i].isRunning()) pastCommandResults[i] = true;
//		}
//		for(int i = 0; i < triggers.length; i++)
//		{
//			if(triggers[i].get()) pastTriggerResults[i] = true;
//		}
//		
//		for(boolean commandResult: pastCommandResults)
//		{
//			if(!commandResult) return false;
//		}
//		for(boolean triggerResult: pastTriggerResults)
//		{
//			if(!triggerResult) return false;
//		}
//		return true;
//	}
//	
//	private static boolean[] generatePastCommandResults(Command[] commands)
//	{
//		boolean[] pastCommandResults = new boolean[commands.length];
//		for(int i = 0; i < pastCommandResults.length; i++)
//		{
//			pastCommandResults[i] = commands[i].isRunning();
//		}
//		return pastCommandResults;
//	}
//	private static boolean[] generatePastTriggerResults(Trigger[] triggers)
//	{
//		boolean[] pastTriggerResults = new boolean[triggers.length];
//		for(int i = 0; i < pastTriggerResults.length; i++)
//		{
//			pastTriggerResults[i] = triggers[i].get();
//		}
//		return pastTriggerResults;
//	}
//	
//	protected void checkForInterruption() throws InterruptedException
//	{
//		sequencer.stop();
//	}
//	
//	private class QueuedCommand
//	{
//		private Command commandToRun;
//		private Command[] commandsToWaitFor;
//		private Trigger[] triggersToWaitFor;
//		private boolean[] pastCommandResults;
//		private boolean[] pastTriggerResults;
//		
//		public QueuedCommand(Command commandToRun, Command[] commandsToWaitFor, Trigger[] triggersToWaitFor)
//		{
//			this.commandToRun = commandToRun;
//			this.commandsToWaitFor = commandsToWaitFor;
//			this.triggersToWaitFor = triggersToWaitFor;
//			this.pastCommandResults = generatePastCommandResults(commandsToWaitFor);
//			this.pastTriggerResults = generatePastTriggerResults(triggersToWaitFor);
//		}
//		
//		public Command getCommand()
//		{
//			return commandToRun;
//		}
//		public boolean isReadyToRun()
//		{
//			return CommandGroupPlus.isReadyToRun(commandsToWaitFor, pastCommandResults, triggersToWaitFor, pastTriggerResults);
//		}
//	}
//	
//	private class Sequencer implements Runnable
//	{
//		private boolean sequenceFinished = false;
//		
//		@Override
//		public void run()
//		{
//			try
//			{
//				sequence();
//			} 
//			catch(InterruptedException e) {}
//			finally
//			{
//				for(int i = 0; i < commandsStarted.size(); i++)
//				{
//					commandsStarted.get(i).cancel();
//				}
//				sequenceFinished = true;
//			}
//		}
//		
//		public boolean isFinished()
//		{
//			return sequenceFinished;
//		}
//		
//		public void stop() throws InterruptedException
//		{
//			if(Thread.interrupted()) throw new InterruptedException();
//		}
//	}
//}
