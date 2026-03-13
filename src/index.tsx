import { NativeModules } from 'react-native';

const { KeyboardMode } = NativeModules;

export function setKeyboardMode(mode: 'pan' | 'resize') {
  if (KeyboardMode?.setMode) {
    KeyboardMode.setMode(mode);
  } else {
    console.warn('KeyboardMode native module not found');
  }
}
