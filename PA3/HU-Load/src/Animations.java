class Animations
{
    /**
     * Plays the drill's flying animation
     * 
     * @param drill Drill object that will use the animation
     */
    public static void drillUpAnim(Drill drill) {
        Runnable task = () -> {
                try {
                    drill.getCharacterImageView().setImage(ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_26.png")));
                    Thread.sleep(100);
                    drill.getCharacterImageView().setImage(ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_27.png")));
                    Thread.sleep(100);
                    drill.getCharacterImageView().setImage(ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_26.png")));
                } catch (InterruptedException e) {
                }
        };
        new Thread(task).start();// Start 2 millisecond animation thread
    }
}