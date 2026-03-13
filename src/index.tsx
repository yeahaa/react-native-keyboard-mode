import { NativeModules } from 'react-native';

const { KeyboardMode } = NativeModules;

export function setKeyboardMode(mode: 'pan' | 'resize') {
  KeyboardMode.setMode(mode);
}
