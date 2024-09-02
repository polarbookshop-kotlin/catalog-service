./gradlew bootBuildImage \
  --imageName ghcr.io/polarbookshop-kotlin/catalog-service \
  --publishImage \
  -PregistryUrl=ghcr.io \
  -PregistryUsername=polarbookshop-kotlin \
  -PregistryToken="$GHCR_TOKEN" \
  --builder dashaun/builder:tiny