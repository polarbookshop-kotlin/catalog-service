./gradlew bootBuildImage \
  --imageName ghcr.io/polarbookshop-kotlin/catalog-service \
  --publishImage \
  -PregistryUrl=ghcr.io \
  -PregistryUsername=polarbookshop-kotlin \
  -PregistryToken="$GHCR_TOKEN" \
  --builder ghcr.io/thomasvitale/java-builder-arm64