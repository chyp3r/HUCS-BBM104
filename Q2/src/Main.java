class Main {
    public static void main(String[] args) {
        String[] classData = FileIO.readFile(args[0], false, false);
        String[] decorationData = FileIO.readFile(args[1], false, false);

        ClassroomBuilder cs = new ClassroomBuilder();
        BuilderDirector bd = new BuilderDirector();

        BuildStarter.startBuilding(decorationData, classData, args,cs,bd);
    }
}

/*
 * Used source
 * https://medium.com/@serhatats/builder-design-pattern-nedir-6146f5c39ecb#
 * https://medium.com/kodcular/builder-design-pattern-kurucu-kalıbı-nedir-bdfed7328906
 * http://cagataykiziltan.net/tasarim-kaliplari-design-patterns/1-creational-tasarim-kaliplari/builder-design-patttern/
 */