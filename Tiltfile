custom_build(
    ref = 'catalog-service',
    command = './gradlew bootBuildImage --imageName $EXPECTED_REF',
    deps = ['build.gradle.kts', 'src'],
)

k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

k8s_resource('catalog-service', port_forwards=['9001'])