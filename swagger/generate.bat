set version=2.0.16
set url=https://github.com/Azure/autorest.java/releases/download/v%version%/microsoft.azure-autorest.java-%version%.tgz
autorest %~dp0README.md --use=%url% --reset --preview --implementation-subpackage=blob.implementation --models-subpackage=blob.models --generate-client-interfaces=false --required-parameter-client-methods=false --client-type-prefix=Generated