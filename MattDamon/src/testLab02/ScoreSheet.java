package testLab02;


/**
 * The Class ScoreSheet.
 */
public class ScoreSheet {
	private final int MAX_FRAMES = 10;
	
	private int currentFrameIndex; // current index position in ARRAY [0, MAX_FRAMES)
	private Frame[] frames;

	/**
	 * Instantiates a new score sheet.
	 */
	public ScoreSheet() {
		currentFrameIndex = 0;
		frames = new Frame[MAX_FRAMES];
		for (int i = 0; i < MAX_FRAMES; i++)
			frames[i] = new Frame();
	}

	/**
	 * Throws the ball.
	 *
	 * @param pinsKnockedDown number of pins knocked down
	 * 
	 * @throws UnsupportedOperationException if frame limit has been reached
	 * @throws IllegalArgumentException if illegal number of pins passed as parameter
	 */
	public void throwBall(int pinsKnockedDown) {
		if (currentFrameIndex >= MAX_FRAMES)
			throw new UnsupportedOperationException("Cannot throw more than 10 frames.");
		if (!frames[currentFrameIndex].throwBall(pinsKnockedDown))
			throw new IllegalArgumentException("Illegal # of pins passed to throwBall(int).");

		if (frames[currentFrameIndex].isStrike() || frames[currentFrameIndex].throw2 != -1)
			currentFrameIndex++;
	}

	/**
	 * Gets the frame score.
	 *
	 * @param frameIndex the frame to retrieve score from [1, MAX_FRAMES]
	 * @return the frame score
	 * 
	 * @throws IllegalArgumentException if frame index outside of [1, MAX_FRAMES]
	 */
	public int getFrameScore(int frameIndex) {
		if (frameIndex < 1 || frameIndex > MAX_FRAMES)
			throw new IllegalArgumentException("Invalid frame index.");

		int frameScore = frames[frameIndex - 1].getScore();

		// if strike/spare and there exists a next frame: add to this frame's score
		if ((frames[frameIndex - 1].isStrike() || frames[frameIndex - 1].isSpare()) && frameIndex < MAX_FRAMES)
			frameScore += frames[frameIndex].getThrowScore(1);
		// if strike and there exists a next next frame: add to this frame's score
		if (frames[frameIndex - 1].isStrike() && frameIndex < MAX_FRAMES) {
			if (frames[frameIndex].isStrike() && frameIndex+1 < MAX_FRAMES) {
				frameScore += frames[frameIndex+1].getThrowScore(1);
				//Two strikes in a row
			} else {
				frameScore += frames[frameIndex].getThrowScore(2);
			}
		}

		return frameScore;
	}

	/**
	 * Gets the score of a specific throw within a specific frame.
	 *
	 * @param frameIndex the frame to retrieve score from [1, MAX_FRAMES]
	 * @param throwIndex the throw to retrieve score of [1, 2]
	 * @return the throw score
	 * 
	 * @throws IllegalArgumentException if frame index outside of [1, MAX_FRAMES]
	 *									or throw index outside of [1, 2]
	 */
	public int getFrameThrowScore(int frameIndex, int throwIndex) {
		if (frameIndex < 1 || frameIndex > MAX_FRAMES || throwIndex < 1 || throwIndex > 2)
			throw new IllegalArgumentException("Invalid frame or throw index.");
		
		 return frames[frameIndex - 1].getThrowScore(throwIndex);
	}

	/**
	 * Gets the current score.
	 *
	 * @return the current score
	 */
	public int getCurrentScore() {
		int currentScore = 0;
		// currentFrameIndex denotes position in ARRAY [0, MAX_FRAMES)
		for (int i = 0; i < MAX_FRAMES; i++)
			currentScore += getFrameScore(i + 1);

		return currentScore;
	}

	private class Frame {
		/// The number of pins that were knocked down in a given throw.
		/// -1 is used to signify a throw that has not occurred.
		/// -1 is the default value.
		private int throw1;

		/// The number of pins that were knocked down in a given throw.
		/// -1 is used to signify a throw that has not occurred.
		/// -1 is the default value.
		private int throw2;

		Frame() {
			throw1 = -1;
			throw2 = -1;
		}

		/// Returns the score of the frame.
		/// Returns -1 if either throw one and throw two have not been
		/// completed.
		private int getScore() {
			if (throw1 == -1) {
				// The frame has not occurred yet
				// if throw1 is negative, throw2 should also be negative.
				return 0;

			} else if (throw2 == -1) {
				return throw1;

			} else {
				// Then throw1 >= 0 && throw2 >= 0
				return throw1 + throw2;
			}
		}
		
		//Returns the score of a specific throw.
		private int getThrowScore(int throwIndex){
			if (throwIndex == 1)
				return (throw1 == -1)? 0: throw1;
			else
				return (throw2 == -1)? 0: throw2;
		}

		/// Returns true if the frame is a strike.
		/// This means that the first throw must be a 10.
		private boolean isStrike() {
			return throw1 == 10;
		}

		/// Returns true if the frame is a strike.
		/// This occurs when:
		/// - First throw is 0 and second throw is 10
		/// - First throw is 1...9 and second throw is 10-firstThrow
		private boolean isSpare() {
			return (throw1 == 0 && throw2 == 10) ||
			// throw1 is 1 to 9 (inclusive) and the first and second throw are
			// equal to 10.
					(throw1 > 0 && throw1 < 10 && throw1 + throw2 == 10);
		}

		/// Ensures that the number of pins is valid
		/// If throw1 has not yet occured, pins will be set to throw1
		/// If throw1 has occurred, but throw2 has not, pins will be set to
		/// throw2
		/// If throw1 and throw2 has occurred, neither throw1 nor throw2 will be
		/// modified, and will return false
		private boolean throwBall(int pins) {
			// Verify that the amount of pins is valid
			if (pins < 0 || pins > 10) {
				// Amount of pins is invalid
				return false;
			}

			if (throw1 == -1) {
				// Then it is the first throw
				throw1 = pins;
				return true;

			} else if (throw2 == -1) {
				// Then it is the second throw

				if (throw1 + pins > 10) {
					// Amount of pins is invalid because it exceeds 10 within
					// one frame
					// By adding the first throw and the new pins.
					return false;
				}
				// Number of pins is valid
				throw2 = pins;
				return true;

			} else {
				return false;
			}
		}
	}
}