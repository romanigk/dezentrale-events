{ }:
let
     pkgs = import (builtins.fetchGit {
         name = "nixpkgs-with-gradle-8.8";
         url = "https://github.com/NixOS/nixpkgs/";
         ref = "refs/heads/nixpkgs-unstable";
         rev = "05bbf675397d5366259409139039af8077d695ce";
     }) {};
in
    pkgs.mkShell {
    name = "dev-environment";
    buildInputs = [
        pkgs.gradle
        pkgs.temurin-bin
        pkgs.kotlin
    ];
    shellHook = ''
        echo "Welcome to dezentrale-events dev environment..."
    '';
    }
