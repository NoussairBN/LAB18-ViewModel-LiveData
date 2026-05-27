# LAB 18 : ViewModel et LiveData en Android

## 📝 Présentation du projet
Ce projet est un laboratoire pratique dédié à l'étude et à l'implémentation des **Android Architecture Components**. L'objectif est de concevoir une application robuste capable de maintenir son état face aux événements complexes du cycle de vie Android, tels que les rotations d'écran, les changements de configuration système et la fermeture de processus par le système (Process Death).

---

## Problématique : Les défis du Cycle de Vie
Dans le développement Android standard, une `Activity` est un composant éphémère. Elle est détruite et recrée lors de chaque changement de configuration (ex: rotation, changement de langue, passage au mode sombre).
- **Conséquence** : Sans une architecture adaptée, toutes les données en mémoire vive (variables d'instance) sont réinitialisées.
- **Limites du passé** : La gestion manuelle via `onSaveInstanceState` est fastidieuse, limitée en taille de données, et mélange la logique métier avec le code de l'interface.

---

## 🛠Solutions implémentées (Architecture MVVM)

L'application suit le pattern **MVVM (Model-View-ViewModel)** pour garantir une séparation stricte des responsabilités :

### 1. ViewModel (`CounterViewModel.java`)
Il sert d'entrepôt de données persistant pour l'UI. Contrairement à l'Activité, il n'est pas détruit lors des changements de configuration. Il conserve l'état logique de l'application.

### 2. LiveData (Communication Réactive)
Nous utilisons `LiveData` pour diffuser les changements de données du ViewModel vers l'Activité.
- **Lifecycle-aware** : Le LiveData ne notifie l'Activité que si celle-ci est active (STARTED/RESUMED), évitant ainsi les fuites de mémoire et les plantages.
- **Mise à jour automatique** : L'interface réagit instantanément aux modifications des données sources.

### 3. SavedStateHandle (Persistance Ultime)
Pour contrer le **Process Death** (lorsque le système tue l'application en arrière-plan pour libérer de la RAM), nous avons intégré `SavedStateHandle`. Cela permet au ViewModel de sauvegarder ses données dans un bundle persistant géré par le système, assurant une restauration parfaite même après une fermeture forcée.

---

## Fonctionnalités
- **Compteur interactif** : Fonctions d'incrémentation, décrémentation et remise à zéro.
- **Robustesse Totale** : Résistance prouvée à la rotation, au changement de thème (Sombre/Clair) et à la mort du processus.
- **Interface découplée** : L'Activité ne contient aucune logique de calcul, elle se contente d'observer.

---

## Protocole de Test & Démonstrations Vidéos

Le dossier `videos/` contient les enregistrements de nos sessions de test :

### 🔴 Test 1 : Mise en évidence du problème initial
Démonstration de la perte de données sans architecture Jetpack.
- **Scénario** : Incrémenter le compteur -> Rotation de l'écran.
- **Observation** : Le compteur revient immédiatement à zéro.

  

https://github.com/user-attachments/assets/59f2087c-dacc-403b-956a-4b289fd7b335



### 🟢 Test 2 : Validation de la Solution Complète (4 scénarios)
Cette vidéo démontre la stabilité finale de l'application après refactorisation :
1.  **Survie à la Rotation** : Le compteur conserve sa valeur après plusieurs changements d'orientation.
2.  **Continuité du Thème** : Le passage du mode Clair au mode Sombre n'affecte pas l'état du compteur.
3.  **Survie au Process Death** : Simulation via la commande :
    `adb shell am kill com.example.viewmodellivedatademoenrichi`
    Au redémarrage, la valeur est restaurée grâce au `SavedStateHandle`.
4.  **Découplage LiveData** : Test réalisé en commentant le code d'observation pour prouver que l'UI est totalement dépendante du flux de données `LiveData`.



https://github.com/user-attachments/assets/d0586c41-3bb0-4060-a9c9-65422e3ab871



---

## 💻 Aperçu du Code Clé

### Observation dans l'Activité
```java
viewModel.getCount().observe(this, newCount -> {
    tvCount.setText(String.valueOf(newCount));
});
```

### Persistance dans le ViewModel
```java
public void increment() {
    int current = handle.get("count_key") != null ? handle.get("count_key") : 0;
    handle.set("count_key", current + 1);
}
```

---

## 🛠️ Stack Technique
- **Langage** : Java
- **Architecture** : MVVM
- **Librairies Jetpack** : ViewModel, LiveData, SavedStateHandle
- **Outils** : Android Studio, ADB (Android Debug Bridge)

---
*Réalisé par **Bouanane** dans le cadre d'un laboratoire sur les composants d'architecture Android.*
