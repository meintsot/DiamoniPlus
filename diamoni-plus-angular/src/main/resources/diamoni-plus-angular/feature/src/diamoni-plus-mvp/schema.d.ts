export interface Schema {
  name: string;
  path?: string;
  project?: string;
  presenter?: boolean;
  style: Style;
  spec?: boolean;
}

export declare enum Style {
  Css = 'css',
  Less = 'less',
  Sass = 'sass',
  Scss = 'scss',
  Styl = 'styl'
}
