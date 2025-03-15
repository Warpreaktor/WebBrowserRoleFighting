package mechanic.interfaces;

import hero.Hero;

public interface Effect extends Switchable {

   void impose(Hero target);

    String getName();
}
