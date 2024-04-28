{
  mode: 'production',
  resolve: {
    modules: [
      'node_modules'
    ]
  },
  plugins: [
    TeamCityErrorPlugin {}
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        use: [
          'source-map-loader'
        ],
        enforce: 'pre'
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: 'style-loader',
            options: {}
          },
          {
            loader: 'css-loader',
            options: {}
          }
        ],
        exclude: undefined,
        include: undefined
      }
    ]
  },
  entry: {
    main: [
      'C:\\Users\\propo\\IdeaProjects\\whiteboard\\build\\js\\packages\\whiteboard\\kotlin\\whiteboard.js'
    ]
  },
  output: {
    path: 'C:\\Users\\propo\\IdeaProjects\\whiteboard\\build\\distributions',
    filename: [Function: filename],
    library: 'whiteboard',
    libraryTarget: 'umd',
    globalObject: 'this'
  },
  devtool: 'source-map',
  ignoreWarnings: [
    /Failed to parse source map/
  ],
  devServer: {
    open: true,
    static: [
      'C:\\Users\\propo\\IdeaProjects\\whiteboard\\build\\processedResources\\js\\main'
    ],
    client: {
      overlay: {
        errors: true,
        warnings: false
      }
    }
  },
  stats: {
    warnings: false,
    errors: false
  }
}