import {ImageFileType} from "./model";
import * as _ from 'lodash';

export class Utils {

  private static readonly PRECEDING_DATA = 'base64,';

  static removeDataURL(data: string): string {
    if (data.includes(this.PRECEDING_DATA)) {
      return data.substring(data.indexOf(this.PRECEDING_DATA) + this.PRECEDING_DATA.length);
    }

    return data;
  }

  static createDataUrl(imageFile: ImageFileType): string {
    return `data:${imageFile.mime};${this.PRECEDING_DATA}${imageFile.data}`;
  }

  static isNullOrUndefined(value: any) {
    return value === null || value === undefined;
  }

  static isStringBlank(value: string | null) {
    return this.isNullOrUndefined(value) || value === '';
  }

  static areObjectsEqual(objA: any, objB: any): boolean {
    for (const [key, value] of Object.entries(objA)) {
      if (!_.isEqual(value, objB[key])) {
        return false;
      }
    }
    return true;
  }
}
