import { Button, View } from 'react-native';
import { setKeyboardMode } from 'react-native-keyboard-mode';

export default function App() {
  return (
    <View>
      <Button title="Pan" onPress={() => setKeyboardMode('pan')} />
      <Button title="Resize" onPress={() => setKeyboardMode('resize')} />
    </View>
  );
}
