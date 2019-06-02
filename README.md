# PIS_16
PIS_16

Things that are implemented but aren't obvious:
- Cloud and Local data persistency for Highscores and if the Tutorial has been played
- Procedural level generation
- Generation of enemies and orbs via factory
- Changes in AI depending on the temperature and the YPosition of the ghosts

# IA Ghosts:
Introducción:
La inteligencia artificial que hemos implementado en nuestro videojuego se utiliza únicamente para manejar el movimiento y de los fantasmas. 

Para llevarla a cabo hemos hecho uso del patrón Strategy, de modo que tenemos la clase GhostBehaviour de la cual heredan todo el resto de clases BehaviourXXXXX (BehaviourDefault, BehaviourBelowTheLine, BehaviourMoveLeft, BehaviourMoveRight, BehaviourR y BehaviourB). 

Los comportamientos que hay son: 
1.Sigue al jugador.
2.Muévete hacia arriba y cuando no puedas seguir moviéndote hacia arriba busca cómo seguir moviéndote hacia arriba.
3.Si estás demasiado arriba muévete de lado a lado hasta que baje la pantalla lo suficiente.

# Random Map Generation:
Introducción:
La generación aleatoria del mapa está diseñada para que vayas por el camino que vayas, nunca se cierre y te imposibilite seguir jugando. También está diseñada para que haya un buen equilibrio entre muchos caminos y espacios abiertos. 

# Ghost and Orb Spawning:
Tanto para Spawnear Ghosts como para Spawnear Orbs, lo que hacemos es tener en cuenta cuales son los nulls de las filas de bloques más altas (para los Orbes) y más bajas (para los fantasmas). Se selecciona un hueco de toda la fila al azar y se llama a la Factory de Ghost o de Orb para que esta nos cree lo que requerimos. Después se calcula la posición que debería tener y se le asigna.
Son ambas factories las que tienen el conocimiento de que tipo de orb o de ghost tendría que ser añadido según la temperatura.


Finalmente, dejo aquí un link a un archivo en drive donde se explica de manera más detallada los procedimientos de la IA Ghost, de Random Map Generation y Ghost and Orb Spawning: 
https://docs.google.com/document/d/1IZCpMwFaWO0X7SRXmAhaG3CCGtsqTUFz_qwnL_GU_TQ/edit?usp=sharing
