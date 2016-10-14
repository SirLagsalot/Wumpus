

public class Explorer {

    private int[] location;
    private int numArrows;
    private KnowledgeBase kb;
    private WumpusGame world;

    public Explorer(int[] location, int numArrows, byte initialPercepts) {
        this.numArrows = numArrows;
        this.location = location;
        run();
    }

    private void run() {

        while (!dead) {

            //check if forward is safe and unexploreed
            if (kb.ask(/*yo kb, is that spot safe and unexplored */)) {
                kb.tell(world.act(/*Move Forward*/));
            //check if any adjacent spots are safe and unexplored
            } else if (kb.ask(/*eyy kb, is it so?*/)) {
                kb.tell(world.act(/*Turn left/right*/));
            } else if (kb.ask(/*Alright alright, kb is there anywhere thats safe and unexplored?*/)) {
                //right hand wall traversal of safe spaces to reach unexplored spot
            } else if (kb.ask(/*Lets hunt some Wumpus, do we know where one is?*/)) {
                //right hand wall traversal to find Wumpus
                //kill Wumpus
            } else {
                //aight, fk it, pick a random direction and go
            }
        }
    }



    //knowledgebase
    class KnowledgeBase() {

        ArrayList<Sentence> knowledge;

        public KnowledgeBase() {

        }

        public void tell(Sentence sentence) {

            unify(sentence);
            resolve(sentence);
        }

        public boolean ask(Sentence sentence) {

        }

        private void unify(Sentence sentence) {

        }

        private void resolve(Sentence sentence) {

        }
    }

}