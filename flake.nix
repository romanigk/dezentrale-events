{
  description = "Build Kotlin project with Gradle and Nix";
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };
  outputs = { self, nixpkgs }:
    let
      name = "Kotlin development shell";
      src = ./src;
      system = "x86_64-linux";
      pkgs = import nixpkgs { inherit system; };
      updateLocks = pkgs.callPackage ./update-locks.nix { };
    in {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = [
	  pkgs.gradle_8
	  pkgs.temurin-bin-21
	  pkgs.ktlint
	  updateLocks
	];
      };
      packages.${system}.default = pkgs.callPackage ./build.nix { };
    };
}
