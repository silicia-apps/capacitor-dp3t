
  Pod::Spec.new do |s|
    s.name = 'CapacitorDp3tPlugin'
    s.version = '0.0.1'
    s.summary = 'plugin for use of dp3t sdk in capacitor'
    s.license = 'MPL 2'
    s.homepage = 'https://github.com/silicia-apps/capacitor-dp3t-plugin.git'
    s.author = 'Francesco Bozzotta (aka xFr4xx)'
    s.source = { :git => 'local', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end