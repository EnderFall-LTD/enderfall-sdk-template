# EnderFall SDK starter

This CC0 starter builds one portable Java mod into a separate JAR for every selected
Minecraft/loader target. No loader metadata or bootstrap classes are maintained by hand.

## Start here

1. Create a repository from this template.
2. Run the one-time identity task:

   ```powershell
   .\gradlew.bat initializeMod -PmodId=my_mod -PmodName="My Mod" -PmodPackage=com.example.mymod -PmodAuthor="Your Name"
   ```

3. Build every selected target with `.\gradlew.bat buildAll`.
4. Find the target JARs in `build/releases`.

Launch the configured development target with `.\gradlew.bat runClient` or
`.\gradlew.bat runServer`. Select another configured target with, for example,
`.\gradlew.bat runServer '-Penderfall.target=1.20.1-forge'`. Generate portable recipes,
tags, translations, loot, block states, and models with `.\gradlew.bat generateData`.

On Linux or macOS, use `./gradlew` instead. Edit `settings.gradle.kts` to change targets.
Portable code belongs in `src/main`; native escape hatches live under `src/loader`,
`src/version`, or `src/target` and are not covered by the portability guarantee.

For SDK source development, run `../enderfall-sdk/gradlew publishWorkspace` once; the
starter detects that local repository and uses the sibling API sources. Once version
`0.1.0-beta.1` is public, the same project resolves it from public repositories when no
sibling checkout is present.
