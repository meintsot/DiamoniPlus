import {
  Rule,
  SchematicContext,
  Tree,
  apply,
  filter,
  mergeWith,
  noop,
  url, applyTemplates, move,
} from '@angular-devkit/schematics';
import { Schema } from "./schema";
import { normalize, strings } from "@angular-devkit/core";

export function diamoniPlusMVP(options: Schema): Rule {
  return async (_tree: Tree, _context: SchematicContext) => {
    const sourceTemplates = url('./files');

    const sourceParametrizedTemplates = apply(sourceTemplates, [
      !options.presenter ? filter((path) => !path.endsWith('.presenter.ts.template')) : noop(),
      !options.spec ? filter((path) => !path.endsWith('.component.spec.ts.template')) : noop(),
      applyTemplates({
        ...options,
        ...strings
      }),
      move(normalize(options.path ?? 'src/app'))
    ]);

    return mergeWith(sourceParametrizedTemplates);
  };
}
